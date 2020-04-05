import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { TableComponent } from './table.component';
import { TableDetailComponent } from './table-detail.component';
import { TableUpdateComponent } from './table-update.component';
import { TableDeleteDialogComponent } from './table-delete-dialog.component';
import { tableRoute } from './table.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(tableRoute)],
  declarations: [TableComponent, TableDetailComponent, TableUpdateComponent, TableDeleteDialogComponent],
  entryComponents: [TableDeleteDialogComponent]
})
export class JhipsterTableModule {}
