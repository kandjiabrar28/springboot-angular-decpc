import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJury, Jury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';

@Component({
  selector: 'jhi-jury-update',
  templateUrl: './jury-update.component.html'
})
export class JuryUpdateComponent implements OnInit {
  isSaving = false;
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    numjury: [],
    dateCreation: [],
    dateModification: []
  });

  constructor(protected juryService: JuryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jury }) => {
      this.updateForm(jury);
    });
  }

  updateForm(jury: IJury): void {
    this.editForm.patchValue({
      id: jury.id,
      numjury: jury.numjury,
      dateCreation: jury.dateCreation,
      dateModification: jury.dateModification
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jury = this.createFromForm();
    if (jury.id !== undefined) {
      this.subscribeToSaveResponse(this.juryService.update(jury));
    } else {
      this.subscribeToSaveResponse(this.juryService.create(jury));
    }
  }

  private createFromForm(): IJury {
    return {
      ...new Jury(),
      id: this.editForm.get(['id'])!.value,
      numjury: this.editForm.get(['numjury'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJury>>): void {
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
