import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { PVSurveillanceComponent } from './pv-surveillance.component';
import { PVSurveillanceDetailComponent } from './pv-surveillance-detail.component';
import { PVSurveillanceUpdateComponent } from './pv-surveillance-update.component';
import { PVSurveillanceDeleteDialogComponent } from './pv-surveillance-delete-dialog.component';
import { pVSurveillanceRoute } from './pv-surveillance.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(pVSurveillanceRoute)],
  declarations: [
    PVSurveillanceComponent,
    PVSurveillanceDetailComponent,
    PVSurveillanceUpdateComponent,
    PVSurveillanceDeleteDialogComponent
  ],
  entryComponents: [PVSurveillanceDeleteDialogComponent]
})
export class JhipsterPVSurveillanceModule {}
