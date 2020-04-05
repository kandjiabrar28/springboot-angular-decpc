import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITable, Table } from 'app/shared/model/table.model';
import { TableService } from './table.service';
import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from 'app/entities/salle/salle.service';

@Component({
  selector: 'jhi-table-update',
  templateUrl: './table-update.component.html'
})
export class TableUpdateComponent implements OnInit {
  isSaving = false;
  salles: ISalle[] = [];

  editForm = this.fb.group({
    id: [],
    numtable: [],
    salle: []
  });

  constructor(
    protected tableService: TableService,
    protected salleService: SalleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ table }) => {
      this.updateForm(table);

      this.salleService.query().subscribe((res: HttpResponse<ISalle[]>) => (this.salles = res.body || []));
    });
  }

  updateForm(table: ITable): void {
    this.editForm.patchValue({
      id: table.id,
      numtable: table.numtable,
      salle: table.salle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const table = this.createFromForm();
    if (table.id !== undefined) {
      this.subscribeToSaveResponse(this.tableService.update(table));
    } else {
      this.subscribeToSaveResponse(this.tableService.create(table));
    }
  }

  private createFromForm(): ITable {
    return {
      ...new Table(),
      id: this.editForm.get(['id'])!.value,
      numtable: this.editForm.get(['numtable'])!.value,
      salle: this.editForm.get(['salle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITable>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISalle): any {
    return item.id;
  }
}
