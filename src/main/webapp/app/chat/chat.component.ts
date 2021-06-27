import { Component, OnDestroy, OnInit } from '@angular/core';
import { AccountService } from 'app/core';
import { ChatService } from 'app/chat/chat.service';
import { Message } from 'app/shared/model/message.model';

@Component({
  selector: 'jhi-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, OnDestroy {
  currentAccount: any;

  constructor(private accountService: AccountService, private websocketService: ChatService) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;

      this.websocketService.connect({
        type: 'JOINED',
        from: this.currentAccount.id,
        fromUserName: this.currentAccount.login,
        message: null
      });
    });
  }

  ngOnDestroy(): void {
    const message: Message = {
      type: 'LEFT',
      from: this.currentAccount.id,
      fromUserName: this.currentAccount.login,
      message: null
    };
    this.websocketService.sendMessage(message);
    this.websocketService.disconnect();
  }
}
