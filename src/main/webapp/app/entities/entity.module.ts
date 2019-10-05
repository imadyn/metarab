import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ref-bahr',
        loadChildren: () => import('./ref-bahr/ref-bahr.module').then(m => m.MetarabRefBahrModule)
      },
      {
        path: 'ref-rhythm',
        loadChildren: () => import('./ref-rhythm/ref-rhythm.module').then(m => m.MetarabRefRhythmModule)
      },
      {
        path: 'type-tb',
        loadChildren: () => import('./type-tb/type-tb.module').then(m => m.MetarabTypeTBModule)
      },
      {
        path: 'ref-alphabet',
        loadChildren: () => import('./ref-alphabet/ref-alphabet.module').then(m => m.MetarabRefAlphabetModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabEntityModule {}
