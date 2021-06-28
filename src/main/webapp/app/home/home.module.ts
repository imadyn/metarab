import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MetarabSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { SearchBahrBaitModule } from 'app/search-bahr-bait/search-bahr-bait.module';

@NgModule({
  imports: [MetarabSharedModule, SearchBahrBaitModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabHomeModule {}
