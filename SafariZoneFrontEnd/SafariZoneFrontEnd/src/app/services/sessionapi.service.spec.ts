import { TestBed } from '@angular/core/testing';

import { SessionapiService } from './sessionapi.service';

describe('SessionapiService', () => {
  let service: SessionapiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionapiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
