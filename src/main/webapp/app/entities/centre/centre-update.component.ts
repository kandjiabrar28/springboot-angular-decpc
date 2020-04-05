import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICentre, Centre } from 'app/shared/model/centre.model';
import { CentreService } from './centre.service';

@Component({
  selector: 'jhi-centre-update',
  templateUrl: './centre-update.component.html'
})
export class CentreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    region: [],
    departement: [],
    telephone: [],
    email: []
  });

  constructor(protected centreService: CentreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centre }) => {
      this.updateForm(centre);
    });
  }

  updateForm(centre: ICentre): void {
    this.editForm.patchValue({
      id: centre.id,
      nom: centre.nom,
      region: centre.region,
      departement: centre.departement,
      telephone: centre.telephone,
      email: centre.email
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centre = this.createFromForm();
    if (centre.id !== undefined) {
      this.subscribeToSaveResponse(this.centreService.update(centre));
    } else {
      this.subscribeToSaveResponse(this.centreService.create(centre));
    }
  }

  private createFromForm(): ICentre {
    return {
      ...new Centre(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      region: this.editForm.get(['region'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentre>>): void {
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
