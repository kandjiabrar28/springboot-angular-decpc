import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlageCopie, PlageCopie } from 'app/shared/model/plage-copie.model';
import { PlageCopieService } from './plage-copie.service';
import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';

@Component({
  selector: 'jhi-plage-copie-update',
  templateUrl: './plage-copie-update.component.html'
})
export class PlageCopieUpdateComponent implements OnInit {
  isSaving = false;
  correcteurs: ICorrecteur[] = [];

  editForm = this.fb.group({
    id: [],
    plage: [],
    typecopie: [],
    nombrecopie: [],
    correcteur: []
  });

  constructor(
    protected plageCopieService: PlageCopieService,
    protected correcteurService: CorrecteurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plageCopie }) => {
      this.updateForm(plageCopie);

      this.correcteurService.query().subscribe((res: HttpResponse<ICorrecteur[]>) => (this.correcteurs = res.body || []));
    });
  }

  updateForm(plageCopie: IPlageCopie): void {
    this.editForm.patchValue({
      id: plageCopie.id,
      plage: plageCopie.plage,
      typecopie: plageCopie.typecopie,
      nombrecopie: plageCopie.nombrecopie,
      correcteur: plageCopie.correcteur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plageCopie = this.createFromForm();
    if (plageCopie.id !== undefined) {
      this.subscribeToSaveResponse(this.plageCopieService.update(plageCopie));
    } else {
      this.subscribeToSaveResponse(this.plageCopieService.create(plageCopie));
    }
  }

  private createFromForm(): IPlageCopie {
    return {
      ...new PlageCopie(),
      id: this.editForm.get(['id'])!.value,
      plage: this.editForm.get(['plage'])!.value,
      typecopie: this.editForm.get(['typecopie'])!.value,
      nombrecopie: this.editForm.get(['nombrecopie'])!.value,
      correcteur: this.editForm.get(['correcteur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlageCopie>>): void {
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
