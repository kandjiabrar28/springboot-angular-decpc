import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';
import { SpecialiteDeleteDialogComponent } from './specialite-delete-dialog.component';

@Component({
  selector: 'jhi-specialite',
  templateUrl: './specialite.component.html'
})
export class SpecialiteComponent implements OnInit, OnDestroy {
  specialites?: ISpecialite[];
  eventSubscriber?: Subscription;

  constructor(protected specialiteService: SpecialiteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpecialites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpecialite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSpecialites(): void {
    this.eventSubscriber = this.eventManager.subscribe('specialiteListModification', () => this.loadAll());
  }

  delete(specialite: ISpecialite): void {
    const modalRef = this.modalService.open(SpecialiteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.specialite = specialite;
  }
}
