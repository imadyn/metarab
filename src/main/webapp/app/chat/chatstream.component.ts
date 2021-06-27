import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core';
import { ChatService } from 'app/chat/chat.service';
import { Message } from 'app/shared/model/message.model';

@Component({
  selector: 'jhi-chat-stream',
  templateUrl: './chatstream.component.html',
  styleUrls: ['./chatstream.component.scss']
})
export class ChatStreamComponent implements OnInit {
  currentAccount: any;
  message = '';
  publishedMessage: Message[] = [];
  showTypingIndicator = false;
  typingUser: string;

  constructor(private accountService: AccountService, private websocketService: ChatService) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
      this.websocketService.subscribe();
      this.websocketService.receive().subscribe(
        message => {
          console.log('message received: ' + message);
          if (message.type === 'MESSAGE') {
            this.publishedMessage.push(message);
          } else if (message.type === 'TYPING' && message.from !== this.currentAccount.id) {
            this.showUserTypingIndicator(message.fromUserName);
          }
        },
        err => console.log(err)
      );
    });
  }

  sendMessage() {
    const msg = this.message;
    if (!msg || msg === '') {
      return;
    }
    const message: Message = {
      type: 'MESSAGE',
      from: this.currentAccount.id,
      fromUserName: this.currentAccount.login,
      message: msg
    };
    this.websocketService.sendMessage(message);

    this.message = '';
  }

  sendTypeIndicator() {
    const message: Message = {
      type: 'TYPING',
      from: this.currentAccount.id,
      fromUserName: this.currentAccount.login,
      message: null
    };
    this.websocketService.sendMessage(message);
  }

  showUserTypingIndicator(userName: string) {
    this.typingUser = userName;
    this.showTypingIndicator = true;
    setTimeout(() => {
      this.hideUserTypingIndicator();
    }, 1000);
  }

  hideUserTypingIndicator() {
    if (this.showTypingIndicator) {
      this.showTypingIndicator = false;
    }
  }

  trackElement(index: number, element: any) {
    return index;
  }
}
