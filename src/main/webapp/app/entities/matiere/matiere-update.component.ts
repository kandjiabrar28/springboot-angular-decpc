import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatiere, Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html'
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;
  correcteurs: ICorrecteur[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    libmatiere: [],
    noteelimin: [],
    coefficient: [],
    dateCreation: [],
    dateModification: [],
    correcteurs: []
  });

  constructor(
    protected matiereService: MatiereService,
    protected correcteurService: CorrecteurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.updateForm(matiere);

      this.correcteurService.query().subscribe((res: HttpResponse<ICorrecteur[]>) => (this.correcteurs = res.body || []));
    });
  }

  updateForm(matiere: IMatiere): void {
    this.editForm.patchValue({
      id: matiere.id,
      libmatiere: matiere.libmatiere,
      noteelimin: matiere.noteelimin,
      coefficient: matiere.coefficient,
      dateCreation: matiere.dateCreation,
      dateModification: matiere.dateModification,
      correcteurs: matiere.correcteurs
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiere = this.createFromForm();
    if (matiere.id !== undefined) {
      this.subscribeToSaveResponse(this.matiereService.update(matiere));
    } else {
      this.subscribeToSaveResponse(this.matiereService.create(matiere));
    }
  }

  private createFromForm(): IMatiere {
    return {
      ...new Matiere(),
      id: this.editForm.get(['id'])!.value,
      libmatiere: this.editForm.get(['libmatiere'])!.value,
      noteelimin: this.editForm.get(['noteelimin'])!.value,
      coefficient: this.editForm.get(['coefficient'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      correcteurs: this.editForm.get(['correcteurs'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
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

  getSelected(selectedVals: ICorrecteur[], option: ICorrecteur): ICorrecteur {
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
