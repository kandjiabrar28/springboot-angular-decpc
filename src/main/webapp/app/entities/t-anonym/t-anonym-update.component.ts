import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITAnonym, TAnonym } from 'app/shared/model/t-anonym.model';
import { TAnonymService } from './t-anonym.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat/candidat.service';

@Component({
  selector: 'jhi-t-anonym-update',
  templateUrl: './t-anonym-update.component.html'
})
export class TAnonymUpdateComponent implements OnInit {
  isSaving = false;
  candidats: ICandidat[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    numanonym: [],
    dateCreation: [],
    dateModification: [],
    candidat: []
  });

  constructor(
    protected tAnonymService: TAnonymService,
    protected candidatService: CandidatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tAnonym }) => {
      this.updateForm(tAnonym);

      this.candidatService.query().subscribe((res: HttpResponse<ICandidat[]>) => (this.candidats = res.body || []));
    });
  }

  updateForm(tAnonym: ITAnonym): void {
    this.editForm.patchValue({
      id: tAnonym.id,
      numanonym: tAnonym.numanonym,
      dateCreation: tAnonym.dateCreation,
      dateModification: tAnonym.dateModification,
      candidat: tAnonym.candidat
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tAnonym = this.createFromForm();
    if (tAnonym.id !== undefined) {
      this.subscribeToSaveResponse(this.tAnonymService.update(tAnonym));
    } else {
      this.subscribeToSaveResponse(this.tAnonymService.create(tAnonym));
    }
  }

  private createFromForm(): ITAnonym {
    return {
      ...new TAnonym(),
      id: this.editForm.get(['id'])!.value,
      numanonym: this.editForm.get(['numanonym'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      candidat: this.editForm.get(['candidat'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITAnonym>>): void {
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

  trackById(index: number, item: ICandidat): any {
    return item.id;
  }
}
