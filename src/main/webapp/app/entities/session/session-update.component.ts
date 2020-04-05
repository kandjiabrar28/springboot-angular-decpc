import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from 'app/entities/examen/examen.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';

type SelectableEntity = IExamen | ISpecialite;

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  examen: IExamen[] = [];
  specialites: ISpecialite[] = [];
  dateSessionDp: any;

  editForm = this.fb.group({
    id: [],
    dateSession: [],
    examen: [],
    specialite: []
  });

  constructor(
    protected sessionService: SessionService,
    protected examenService: ExamenService,
    protected specialiteService: SpecialiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.examenService.query().subscribe((res: HttpResponse<IExamen[]>) => (this.examen = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));
    });
  }

  updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      dateSession: session.dateSession,
      examen: session.examen,
      specialite: session.specialite
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      dateSession: this.editForm.get(['dateSession'])!.value,
      examen: this.editForm.get(['examen'])!.value,
      specialite: this.editForm.get(['specialite'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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
