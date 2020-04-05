import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { PlageCopieComponent } from './plage-copie.component';
import { PlageCopieDetailComponent } from './plage-copie-detail.component';
import { PlageCopieUpdateComponent } from './plage-copie-update.component';
import { PlageCopieDeleteDialogComponent } from './plage-copie-delete-dialog.component';
import { plageCopieRoute } from './plage-copie.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(plageCopieRoute)],
  declarations: [PlageCopieComponent, PlageCopieDetailComponent, PlageCopieUpdateComponent, PlageCopieDeleteDialogComponent],
  entryComponents: [PlageCopieDeleteDialogComponent]
})
export class JhipsterPlageCopieModule {}
