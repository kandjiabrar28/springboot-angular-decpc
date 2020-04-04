import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITables, Tables } from 'app/shared/model/tables.model';
import { TablesService } from './tables.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from 'app/entities/salle/salle.service';

type SelectableEntity = ICandidat | ISalle;

@Component({
  selector: 'jhi-tables-update',
  templateUrl: './tables-update.component.html'
})
export class TablesUpdateComponent implements OnInit {
  isSaving = false;
  candidats: ICandidat[] = [];
  salles: ISalle[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    numtable: [],
    dateCreation: [],
    dateModification: [],
    candidat: [],
    salle: []
  });

  constructor(
    protected tablesService: TablesService,
    protected candidatService: CandidatService,
    protected salleService: SalleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tables }) => {
      this.updateForm(tables);

      this.candidatService
        .query({ filter: 'tables-is-null' })
        .pipe(
          map((res: HttpResponse<ICandidat[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICandidat[]) => {
          if (!tables.candidat || !tables.candidat.id) {
            this.candidats = resBody;
          } else {
            this.candidatService
              .find(tables.candidat.id)
              .pipe(
                map((subRes: HttpResponse<ICandidat>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICandidat[]) => (this.candidats = concatRes));
          }
        });

      this.salleService.query().subscribe((res: HttpResponse<ISalle[]>) => (this.salles = res.body || []));
    });
  }

  updateForm(tables: ITables): void {
    this.editForm.patchValue({
      id: tables.id,
      numtable: tables.numtable,
      dateCreation: tables.dateCreation,
      dateModification: tables.dateModification,
      candidat: tables.candidat,
      salle: tables.salle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tables = this.createFromForm();
    if (tables.id !== undefined) {
      this.subscribeToSaveResponse(this.tablesService.update(tables));
    } else {
      this.subscribeToSaveResponse(this.tablesService.create(tables));
    }
  }

  private createFromForm(): ITables {
    return {
      ...new Tables(),
      id: this.editForm.get(['id'])!.value,
      numtable: this.editForm.get(['numtable'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      candidat: this.editForm.get(['candidat'])!.value,
      salle: this.editForm.get(['salle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITables>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
