import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { CentreComponent } from './centre.component';
import { CentreDetailComponent } from './centre-detail.component';
import { CentreUpdateComponent } from './centre-update.component';
import { CentreDeleteDialogComponent } from './centre-delete-dialog.component';
import { centreRoute } from './centre.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(centreRoute)],
  declarations: [CentreComponent, CentreDetailComponent, CentreUpdateComponent, CentreDeleteDialogComponent],
  entryComponents: [CentreDeleteDialogComponent]
})
export class JhipsterCentreModule {}
