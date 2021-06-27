import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { ChatComponent } from 'app/chat/chat.component';

export const chatRoute: Routes = [
  {
    path: '',
    component: ChatComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'metarabApp.chat.home.title',
      authorities: ['ROLE_USER']
    }
  }
];
