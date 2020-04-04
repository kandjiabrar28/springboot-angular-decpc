import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { TAnonymComponent } from './t-anonym.component';
import { TAnonymDetailComponent } from './t-anonym-detail.component';
import { TAnonymUpdateComponent } from './t-anonym-update.component';
import { TAnonymDeleteDialogComponent } from './t-anonym-delete-dialog.component';
import { tAnonymRoute } from './t-anonym.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(tAnonymRoute)],
  declarations: [TAnonymComponent, TAnonymDetailComponent, TAnonymUpdateComponent, TAnonymDeleteDialogComponent],
  entryComponents: [TAnonymDeleteDialogComponent]
})
export class JhipsterTAnonymModule {}
