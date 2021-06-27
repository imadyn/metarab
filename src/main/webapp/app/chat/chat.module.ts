import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { MetarabSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { ChatStreamComponent } from 'app/chat/chatstream.component';
import { ChatComponent } from 'app/chat/chat.component';
import { chatRoute } from 'app/chat/chat.route';
import { UsersComponent } from 'app/chat/users.component';

const ENTITY_STATES = [...chatRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ChatComponent, ChatStreamComponent, UsersComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
