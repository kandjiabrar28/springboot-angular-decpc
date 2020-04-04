import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ExamenDeleteDialogComponent } from './examen-delete-dialog.component';

@Component({
  selector: 'jhi-examen',
  templateUrl: './examen.component.html'
})
export class ExamenComponent implements OnInit, OnDestroy {
  examen?: IExamen[];
  eventSubscriber?: Subscription;

  constructor(protected examenService: ExamenService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.examenService.query().subscribe((res: HttpResponse<IExamen[]>) => (this.examen = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExamen();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExamen): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExamen(): void {
    this.eventSubscriber = this.eventManager.subscribe('examenListModification', () => this.loadAll());
  }

  delete(examen: IExamen): void {
    const modalRef = this.modalService.open(ExamenDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examen = examen;
  }
}
