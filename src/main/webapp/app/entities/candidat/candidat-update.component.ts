import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICandidat, Candidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
  selector: 'jhi-candidat-update',
  templateUrl: './candidat-update.component.html'
})
export class CandidatUpdateComponent implements OnInit {
  isSaving = false;
  datenaisDp: any;
  dateCreationDp: any;
  dateModificationDp: any;

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
    dateCreation: [],
    dateModification: []
  });

  constructor(protected candidatService: CandidatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidat }) => {
      this.updateForm(candidat);
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
      dateCreation: candidat.dateCreation,
      dateModification: candidat.dateModification
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
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value
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
}
