import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: () => import('./admin/admin.module').then(m => m.MetarabAdminModule)
        },
        {
          path: 'bahrbait',
          loadChildren: () => import('./search-bahr-bait/search-bahr-bait.module').then(m => m.SearchBahrBaitModule)
        },
        {
          path: 'bahr',
          loadChildren: () => import('./search-bahr/search-bahr.module').then(m => m.SearchBahrModule)
        },
        {
          path: 'chat',
          loadChildren: () => import('./chat/chat.module').then(m => m.ChatModule)
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class MetarabAppRoutingModule {}
