import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { SpecialiteComponent } from './specialite.component';
import { SpecialiteDetailComponent } from './specialite-detail.component';
import { SpecialiteUpdateComponent } from './specialite-update.component';
import { SpecialiteDeleteDialogComponent } from './specialite-delete-dialog.component';
import { specialiteRoute } from './specialite.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(specialiteRoute)],
  declarations: [SpecialiteComponent, SpecialiteDetailComponent, SpecialiteUpdateComponent, SpecialiteDeleteDialogComponent],
  entryComponents: [SpecialiteDeleteDialogComponent]
})
export class JhipsterSpecialiteModule {}
