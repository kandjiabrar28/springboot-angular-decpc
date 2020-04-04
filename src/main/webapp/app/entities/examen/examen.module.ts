import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ExamenComponent } from './examen.component';
import { ExamenDetailComponent } from './examen-detail.component';
import { ExamenUpdateComponent } from './examen-update.component';
import { ExamenDeleteDialogComponent } from './examen-delete-dialog.component';
import { examenRoute } from './examen.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(examenRoute)],
  declarations: [ExamenComponent, ExamenDetailComponent, ExamenUpdateComponent, ExamenDeleteDialogComponent],
  entryComponents: [ExamenDeleteDialogComponent]
})
export class JhipsterExamenModule {}
