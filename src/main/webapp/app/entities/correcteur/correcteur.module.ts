import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { CorrecteurComponent } from './correcteur.component';
import { CorrecteurDetailComponent } from './correcteur-detail.component';
import { CorrecteurUpdateComponent } from './correcteur-update.component';
import { CorrecteurDeleteDialogComponent } from './correcteur-delete-dialog.component';
import { correcteurRoute } from './correcteur.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(correcteurRoute)],
  declarations: [CorrecteurComponent, CorrecteurDetailComponent, CorrecteurUpdateComponent, CorrecteurDeleteDialogComponent],
  entryComponents: [CorrecteurDeleteDialogComponent]
})
export class JhipsterCorrecteurModule {}
