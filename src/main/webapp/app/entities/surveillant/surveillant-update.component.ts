import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISurveillant, Surveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from './surveillant.service';

@Component({
  selector: 'jhi-surveillant-update',
  templateUrl: './surveillant-update.component.html'
})
export class SurveillantUpdateComponent implements OnInit {
  isSaving = false;
  datenaisDp: any;

  editForm = this.fb.group({
    id: [],
    fonction: [],
    prenom: [],
    nom: [],
    provenance: [],
    cni: [],
    telephone: [],
    sexe: [],
    datenais: []
  });

  constructor(protected surveillantService: SurveillantService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveillant }) => {
      this.updateForm(surveillant);
    });
  }

  updateForm(surveillant: ISurveillant): void {
    this.editForm.patchValue({
      id: surveillant.id,
      fonction: surveillant.fonction,
      prenom: surveillant.prenom,
      nom: surveillant.nom,
      provenance: surveillant.provenance,
      cni: surveillant.cni,
      telephone: surveillant.telephone,
      sexe: surveillant.sexe,
      datenais: surveillant.datenais
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const surveillant = this.createFromForm();
    if (surveillant.id !== undefined) {
      this.subscribeToSaveResponse(this.surveillantService.update(surveillant));
    } else {
      this.subscribeToSaveResponse(this.surveillantService.create(surveillant));
    }
  }

  private createFromForm(): ISurveillant {
    return {
      ...new Surveillant(),
      id: this.editForm.get(['id'])!.value,
      fonction: this.editForm.get(['fonction'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      provenance: this.editForm.get(['provenance'])!.value,
      cni: this.editForm.get(['cni'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      datenais: this.editForm.get(['datenais'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurveillant>>): void {
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
