import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpecialite, Specialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';
import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from 'app/entities/niveau/niveau.service';
import { IFiliere } from 'app/shared/model/filiere.model';
import { FiliereService } from 'app/entities/filiere/filiere.service';

type SelectableEntity = INiveau | IFiliere;

@Component({
  selector: 'jhi-specialite-update',
  templateUrl: './specialite-update.component.html'
})
export class SpecialiteUpdateComponent implements OnInit {
  isSaving = false;
  niveaus: INiveau[] = [];
  filieres: IFiliere[] = [];

  editForm = this.fb.group({
    id: [],
    libspec: [],
    niveaus: [],
    filiere: []
  });

  constructor(
    protected specialiteService: SpecialiteService,
    protected niveauService: NiveauService,
    protected filiereService: FiliereService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => {
      this.updateForm(specialite);

      this.niveauService.query().subscribe((res: HttpResponse<INiveau[]>) => (this.niveaus = res.body || []));

      this.filiereService.query().subscribe((res: HttpResponse<IFiliere[]>) => (this.filieres = res.body || []));
    });
  }

  updateForm(specialite: ISpecialite): void {
    this.editForm.patchValue({
      id: specialite.id,
      libspec: specialite.libspec,
      niveaus: specialite.niveaus,
      filiere: specialite.filiere
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialite = this.createFromForm();
    if (specialite.id !== undefined) {
      this.subscribeToSaveResponse(this.specialiteService.update(specialite));
    } else {
      this.subscribeToSaveResponse(this.specialiteService.create(specialite));
    }
  }

  private createFromForm(): ISpecialite {
    return {
      ...new Specialite(),
      id: this.editForm.get(['id'])!.value,
      libspec: this.editForm.get(['libspec'])!.value,
      niveaus: this.editForm.get(['niveaus'])!.value,
      filiere: this.editForm.get(['filiere'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialite>>): void {
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

  getSelected(selectedVals: INiveau[], option: INiveau): INiveau {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
