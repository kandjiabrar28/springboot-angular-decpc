import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlage, Plage } from 'app/shared/model/plage.model';
import { PlageService } from './plage.service';
import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';

@Component({
  selector: 'jhi-plage-update',
  templateUrl: './plage-update.component.html'
})
export class PlageUpdateComponent implements OnInit {
  isSaving = false;
  correcteurs: ICorrecteur[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    plage: [],
    typecopie: [],
    nombrecopie: [],
    dateCreation: [],
    dateModification: [],
    correcteur: []
  });

  constructor(
    protected plageService: PlageService,
    protected correcteurService: CorrecteurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plage }) => {
      this.updateForm(plage);

      this.correcteurService.query().subscribe((res: HttpResponse<ICorrecteur[]>) => (this.correcteurs = res.body || []));
    });
  }

  updateForm(plage: IPlage): void {
    this.editForm.patchValue({
      id: plage.id,
      plage: plage.plage,
      typecopie: plage.typecopie,
      nombrecopie: plage.nombrecopie,
      dateCreation: plage.dateCreation,
      dateModification: plage.dateModification,
      correcteur: plage.correcteur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plage = this.createFromForm();
    if (plage.id !== undefined) {
      this.subscribeToSaveResponse(this.plageService.update(plage));
    } else {
      this.subscribeToSaveResponse(this.plageService.create(plage));
    }
  }

  private createFromForm(): IPlage {
    return {
      ...new Plage(),
      id: this.editForm.get(['id'])!.value,
      plage: this.editForm.get(['plage'])!.value,
      typecopie: this.editForm.get(['typecopie'])!.value,
      nombrecopie: this.editForm.get(['nombrecopie'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      correcteur: this.editForm.get(['correcteur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlage>>): void {
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

  trackById(index: number, item: ICorrecteur): any {
    return item.id;
  }
}
