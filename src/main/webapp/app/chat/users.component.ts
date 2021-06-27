import { Component, OnInit } from '@angular/core';
import { AccountService, User, UserService } from 'app/core';
import { ChatService } from 'app/chat/chat.service';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
@Component({
  selector: 'jhi-users',
  templateUrl: './users.component.html',
  styleUrls: ['./user.component.scss']
})
export class UsersComponent implements OnInit {
  currentAccount: any;
  users: User[] = new Array();

  constructor(
    private accountService: AccountService,
    private userService: UserService,
    private alertService: JhiAlertService,
    private websocketService: ChatService
  ) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });

    this.websocketService.subscribe();

    this.initUserList();
    this.startListening();
  }

  startListening() {
    this.websocketService.receive().subscribe(
      message => {
        console.log('message received: ' + message.type + ' from : ' + message.from + ' id current : ' + this.currentAccount.id);
        if (message.type === 'JOINED') {
          this.setUserStatus(message.from, true);
        } else if (message.type === 'LEFT') {
          this.setUserStatus(message.from, false);
        }
      },
      err => console.log(err)
    );
  }

  initUserList() {
    this.userService
      .queryChat({
        page: 0,
        size: 10
      })
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.users = res.body;
        },
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  setUserStatus(userId: Number, isOnline: boolean) {
    const user: User = this.users.find(u => u.id === userId);
    user.isOnline = isOnline;
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }
}
