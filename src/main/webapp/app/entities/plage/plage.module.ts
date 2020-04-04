import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { PlageComponent } from './plage.component';
import { PlageDetailComponent } from './plage-detail.component';
import { PlageUpdateComponent } from './plage-update.component';
import { PlageDeleteDialogComponent } from './plage-delete-dialog.component';
import { plageRoute } from './plage.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(plageRoute)],
  declarations: [PlageComponent, PlageDetailComponent, PlageUpdateComponent, PlageDeleteDialogComponent],
  entryComponents: [PlageDeleteDialogComponent]
})
export class JhipsterPlageModule {}
