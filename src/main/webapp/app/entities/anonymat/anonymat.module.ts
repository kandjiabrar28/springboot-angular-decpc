import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { AnonymatComponent } from './anonymat.component';
import { AnonymatDetailComponent } from './anonymat-detail.component';
import { AnonymatUpdateComponent } from './anonymat-update.component';
import { AnonymatDeleteDialogComponent } from './anonymat-delete-dialog.component';
import { anonymatRoute } from './anonymat.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(anonymatRoute)],
  declarations: [AnonymatComponent, AnonymatDetailComponent, AnonymatUpdateComponent, AnonymatDeleteDialogComponent],
  entryComponents: [AnonymatDeleteDialogComponent]
})
export class JhipsterAnonymatModule {}
