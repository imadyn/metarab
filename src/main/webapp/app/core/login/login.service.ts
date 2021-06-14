import { Injectable } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Observable } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { Login } from 'app/core/login/login.model';
import { Account } from 'app/core';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {
  }

  login(credentials: Login): Observable<Account> {
    return this.authServerProvider.login(credentials).pipe(flatMap(() => this.accountService.identity(true)));
  }

  loginWithToken(jwt, rememberMe) {
    return this.authServerProvider.loginWithToken(jwt, rememberMe);
  }

  logout() {
    return new Promise((resolve, reject) => {
      this.authServerProvider.logout().subscribe(
        data => {
          this.accountService.identity(true).then(account => {
            resolve(data);
          });
        },
        err => {
          reject(err);
        }
      );
    });
  }
}
