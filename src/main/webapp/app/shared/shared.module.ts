import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MetarabSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [MetarabSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MetarabSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabSharedModule {
  static forRoot() {
    return {
      ngModule: MetarabSharedModule
    };
  }
}
