import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamen, Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';

@Component({
  selector: 'jhi-examen-update',
  templateUrl: './examen-update.component.html'
})
export class ExamenUpdateComponent implements OnInit {
  isSaving = false;
  dateExamenDp: any;

  editForm = this.fb.group({
    id: [],
    nomexamen: [],
    dateExamen: []
  });

  constructor(protected examenService: ExamenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examen }) => {
      this.updateForm(examen);
    });
  }

  updateForm(examen: IExamen): void {
    this.editForm.patchValue({
      id: examen.id,
      nomexamen: examen.nomexamen,
      dateExamen: examen.dateExamen
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examen = this.createFromForm();
    if (examen.id !== undefined) {
      this.subscribeToSaveResponse(this.examenService.update(examen));
    } else {
      this.subscribeToSaveResponse(this.examenService.create(examen));
    }
  }

  private createFromForm(): IExamen {
    return {
      ...new Examen(),
      id: this.editForm.get(['id'])!.value,
      nomexamen: this.editForm.get(['nomexamen'])!.value,
      dateExamen: this.editForm.get(['dateExamen'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>): void {
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
