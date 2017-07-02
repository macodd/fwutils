import { TestBed, inject } from '@angular/core/testing';

import { DelayService } from './delay.service';
import { Delay } from './delay';

describe('When converting seconds to delay', () => {

  let delayService: DelayService;

  beforeEach(() => {
    delayService = new DelayService();
  });

  it('returns 7 seconds for input 7 seconds ', () => {
    const excpected: Delay = { seconds: 7 };
    expect(delayService.convertToDelay(7)).toEqual(excpected);
  });

  it('returns 3 minutes 7 seconds for input 187 seconds ', () => {
    const excpected: Delay = { minutes: 3, seconds: 7 };
    expect(delayService.convertToDelay(187)).toEqual(excpected);
  });

  it('returns 5 hours 3 minutes 7 seconds for input 18187 seconds ', () => {
    const excpected: Delay = { hours: 5, minutes: 3, seconds: 7 };
    expect(delayService.convertToDelay(18187)).toEqual(excpected);
  });

  it('returns 2 days 5 hours 3 minutes 7 seconds for input 190987 seconds ', () => {
    const excpected: Delay = { days: 2, hours: 5, minutes: 3, seconds: 7 };
    expect(delayService.convertToDelay(190987)).toEqual(excpected);
  });
});

describe('DelayService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DelayService]
    });
  });

  it('should be created', inject([DelayService], (service: DelayService) => {
    expect(service).toBeTruthy();
  }));
});
