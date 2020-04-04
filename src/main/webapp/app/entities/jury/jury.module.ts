import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { JuryComponent } from './jury.component';
import { JuryDetailComponent } from './jury-detail.component';
import { JuryUpdateComponent } from './jury-update.component';
import { JuryDeleteDialogComponent } from './jury-delete-dialog.component';
import { juryRoute } from './jury.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(juryRoute)],
  declarations: [JuryComponent, JuryDetailComponent, JuryUpdateComponent, JuryDeleteDialogComponent],
  entryComponents: [JuryDeleteDialogComponent]
})
export class JhipsterJuryModule {}
