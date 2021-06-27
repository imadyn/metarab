import { Injectable } from '@angular/core';
import { Location } from '@angular/common';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import { AuthServerProvider } from 'app/core';
import { ReplaySubject, Subject, Subscription } from 'rxjs';
import { Message } from 'app/shared/model/message.model';

export const WEBSOCKET_URL = '/websocket/chat';
export const WEBSOCKET_RELAY_URL = '/topic/chat';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Stomp.Client | null = null;
  private stompSubscription: Stomp.Subscription | null = null;
  private connectionSubject: ReplaySubject<void> = new ReplaySubject(1);
  private connectionSubscription: Subscription | null = null;
  private listenerSubject: Subject<Message> = new Subject();

  constructor(private authServerProvider: AuthServerProvider, private location: Location) {}

  public connect(msg: Message): void {
    if (this.stompClient && this.stompClient.connected) {
      return;
    }

    const url = this.location.prepareExternalUrl(WEBSOCKET_URL);
    this.stompClient = Stomp.over(new SockJS(url), { protocols: ['v12.stomp'] });
    const headers: Stomp.ConnectionHeaders = {};
    headers['X-Authorization'] = 'Bearer ' + this.authServerProvider.getToken();

    this.stompClient.connect(headers, () => {
      this.connectionSubject.next();
      this.sendMessage(msg);
    });
  }

  subscribe(): void {
    if (this.connectionSubscription) {
      return;
    }

    this.connectionSubscription = this.connectionSubject.subscribe(() => {
      if (this.stompClient) {
        this.stompSubscription = this.stompClient.subscribe(WEBSOCKET_RELAY_URL, (data: Stomp.Message) => {
          this.listenerSubject.next(JSON.parse(data.body));
        });
      }
    });
  }

  disconnect(): void {
    this.unsubscribe();

    this.connectionSubject = new ReplaySubject(1);

    if (this.stompClient) {
      if (this.stompClient.connected) {
        this.stompClient.disconnect();
      }
      this.stompClient = null;
    }
  }

  receive(): Subject<Message> {
    return this.listenerSubject;
  }

  unsubscribe(): void {
    if (this.stompSubscription) {
      this.stompSubscription.unsubscribe();
      this.stompSubscription = null;
    }

    if (this.connectionSubscription) {
      this.connectionSubscription.unsubscribe();
      this.connectionSubscription = null;
    }
  }

  public sendMessage(msg: Message): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(
        WEBSOCKET_RELAY_URL, // destination
        JSON.stringify(msg), // body
        {} // header
      );
    }
  }
}
