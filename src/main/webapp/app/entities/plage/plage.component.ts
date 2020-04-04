import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlage } from 'app/shared/model/plage.model';
import { PlageService } from './plage.service';
import { PlageDeleteDialogComponent } from './plage-delete-dialog.component';

@Component({
  selector: 'jhi-plage',
  templateUrl: './plage.component.html'
})
export class PlageComponent implements OnInit, OnDestroy {
  plages?: IPlage[];
  eventSubscriber?: Subscription;

  constructor(protected plageService: PlageService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.plageService.query().subscribe((res: HttpResponse<IPlage[]>) => (this.plages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlages(): void {
    this.eventSubscriber = this.eventManager.subscribe('plageListModification', () => this.loadAll());
  }

  delete(plage: IPlage): void {
    const modalRef = this.modalService.open(PlageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plage = plage;
  }
}
