import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { SurveillantComponent } from './surveillant.component';
import { SurveillantDetailComponent } from './surveillant-detail.component';
import { SurveillantUpdateComponent } from './surveillant-update.component';
import { SurveillantDeleteDialogComponent } from './surveillant-delete-dialog.component';
import { surveillantRoute } from './surveillant.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(surveillantRoute)],
  declarations: [SurveillantComponent, SurveillantDetailComponent, SurveillantUpdateComponent, SurveillantDeleteDialogComponent],
  entryComponents: [SurveillantDeleteDialogComponent]
})
export class JhipsterSurveillantModule {}
