import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICorrecteur, Correcteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from './correcteur.service';

@Component({
  selector: 'jhi-correcteur-update',
  templateUrl: './correcteur-update.component.html'
})
export class CorrecteurUpdateComponent implements OnInit {
  isSaving = false;
  datenaisDp: any;
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    grade: [],
    prenom: [],
    nom: [],
    provenance: [],
    cni: [],
    telephone: [],
    sexe: [],
    datenais: [],
    dateCreation: [],
    dateModification: []
  });

  constructor(protected correcteurService: CorrecteurService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ correcteur }) => {
      this.updateForm(correcteur);
    });
  }

  updateForm(correcteur: ICorrecteur): void {
    this.editForm.patchValue({
      id: correcteur.id,
      grade: correcteur.grade,
      prenom: correcteur.prenom,
      nom: correcteur.nom,
      provenance: correcteur.provenance,
      cni: correcteur.cni,
      telephone: correcteur.telephone,
      sexe: correcteur.sexe,
      datenais: correcteur.datenais,
      dateCreation: correcteur.dateCreation,
      dateModification: correcteur.dateModification
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const correcteur = this.createFromForm();
    if (correcteur.id !== undefined) {
      this.subscribeToSaveResponse(this.correcteurService.update(correcteur));
    } else {
      this.subscribeToSaveResponse(this.correcteurService.create(correcteur));
    }
  }

  private createFromForm(): ICorrecteur {
    return {
      ...new Correcteur(),
      id: this.editForm.get(['id'])!.value,
      grade: this.editForm.get(['grade'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      provenance: this.editForm.get(['provenance'])!.value,
      cni: this.editForm.get(['cni'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      datenais: this.editForm.get(['datenais'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorrecteur>>): void {
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
