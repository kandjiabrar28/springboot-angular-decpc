import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICandidat, Candidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';
import { ITable } from 'app/shared/model/table.model';
import { TableService } from 'app/entities/table/table.service';

@Component({
  selector: 'jhi-candidat-update',
  templateUrl: './candidat-update.component.html'
})
export class CandidatUpdateComponent implements OnInit {
  isSaving = false;
  tables: ITable[] = [];
  datenaisDp: any;

  editForm = this.fb.group({
    id: [],
    prenom: [],
    nom: [],
    provenance: [],
    cni: [],
    telephone: [],
    sexe: [],
    datenais: [],
    niveau: [],
    table: []
  });

  constructor(
    protected candidatService: CandidatService,
    protected tableService: TableService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidat }) => {
      this.updateForm(candidat);

      this.tableService
        .query({ filter: 'candidat-is-null' })
        .pipe(
          map((res: HttpResponse<ITable[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITable[]) => {
          if (!candidat.table || !candidat.table.id) {
            this.tables = resBody;
          } else {
            this.tableService
              .find(candidat.table.id)
              .pipe(
                map((subRes: HttpResponse<ITable>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITable[]) => (this.tables = concatRes));
          }
        });
    });
  }

  updateForm(candidat: ICandidat): void {
    this.editForm.patchValue({
      id: candidat.id,
      prenom: candidat.prenom,
      nom: candidat.nom,
      provenance: candidat.provenance,
      cni: candidat.cni,
      telephone: candidat.telephone,
      sexe: candidat.sexe,
      datenais: candidat.datenais,
      niveau: candidat.niveau,
      table: candidat.table
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidat = this.createFromForm();
    if (candidat.id !== undefined) {
      this.subscribeToSaveResponse(this.candidatService.update(candidat));
    } else {
      this.subscribeToSaveResponse(this.candidatService.create(candidat));
    }
  }

  private createFromForm(): ICandidat {
    return {
      ...new Candidat(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      provenance: this.editForm.get(['provenance'])!.value,
      cni: this.editForm.get(['cni'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      datenais: this.editForm.get(['datenais'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      table: this.editForm.get(['table'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidat>>): void {
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

  trackById(index: number, item: ITable): any {
    return item.id;
  }
}
