import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { MetarabSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { SearchBahrBaitComponent } from './search-bahr-bait/search-bahr-bait.component';
import { bahrCombineRoute } from 'app/search-bahr-bait/search-bahr-bait/search-bahr-bait.route';

const ENTITY_STATES = [...bahrCombineRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SearchBahrBaitComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  exports: [SearchBahrBaitComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SearchBahrBaitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
