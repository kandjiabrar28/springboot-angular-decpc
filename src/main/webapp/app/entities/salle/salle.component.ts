import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from './salle.service';
import { SalleDeleteDialogComponent } from './salle-delete-dialog.component';

@Component({
  selector: 'jhi-salle',
  templateUrl: './salle.component.html'
})
export class SalleComponent implements OnInit, OnDestroy {
  salles?: ISalle[];
  eventSubscriber?: Subscription;

  constructor(protected salleService: SalleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.salleService.query().subscribe((res: HttpResponse<ISalle[]>) => (this.salles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSalles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISalle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSalles(): void {
    this.eventSubscriber = this.eventManager.subscribe('salleListModification', () => this.loadAll());
  }

  delete(salle: ISalle): void {
    const modalRef = this.modalService.open(SalleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salle = salle;
  }
}
