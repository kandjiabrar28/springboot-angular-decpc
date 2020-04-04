import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INote, Note } from 'app/shared/model/note.model';
import { NoteService } from './note.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat/candidat.service';

type SelectableEntity = IMatiere | ICandidat;

@Component({
  selector: 'jhi-note-update',
  templateUrl: './note-update.component.html'
})
export class NoteUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  candidats: ICandidat[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    note: [],
    dateCreation: [],
    dateModification: [],
    matiere: [],
    candidat: []
  });

  constructor(
    protected noteService: NoteService,
    protected matiereService: MatiereService,
    protected candidatService: CandidatService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.updateForm(note);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.candidatService.query().subscribe((res: HttpResponse<ICandidat[]>) => (this.candidats = res.body || []));
    });
  }

  updateForm(note: INote): void {
    this.editForm.patchValue({
      id: note.id,
      note: note.note,
      dateCreation: note.dateCreation,
      dateModification: note.dateModification,
      matiere: note.matiere,
      candidat: note.candidat
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.createFromForm();
    if (note.id !== undefined) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  private createFromForm(): INote {
    return {
      ...new Note(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      matiere: this.editForm.get(['matiere'])!.value,
      candidat: this.editForm.get(['candidat'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
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
}
