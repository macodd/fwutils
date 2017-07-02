import { FreewarToolsetPage } from './app.po';

describe('freewar-toolset App', () => {
  let page: FreewarToolsetPage;

  beforeEach(() => {
    page = new FreewarToolsetPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
