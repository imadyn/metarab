/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RefBahrComponentsPage, RefBahrDeleteDialog, RefBahrUpdatePage } from './ref-bahr.page-object';

const expect = chai.expect;

describe('RefBahr e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let refBahrUpdatePage: RefBahrUpdatePage;
  let refBahrComponentsPage: RefBahrComponentsPage;
  let refBahrDeleteDialog: RefBahrDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RefBahrs', async () => {
    await navBarPage.goToEntity('ref-bahr');
    refBahrComponentsPage = new RefBahrComponentsPage();
    await browser.wait(ec.visibilityOf(refBahrComponentsPage.title), 5000);
    expect(await refBahrComponentsPage.getTitle()).to.eq('metarabApp.refBahr.home.title');
  });

  it('should load create RefBahr page', async () => {
    await refBahrComponentsPage.clickOnCreateButton();
    refBahrUpdatePage = new RefBahrUpdatePage();
    expect(await refBahrUpdatePage.getPageTitle()).to.eq('metarabApp.refBahr.home.createOrEditLabel');
    await refBahrUpdatePage.cancel();
  });

  it('should create and save RefBahrs', async () => {
    const nbButtonsBeforeCreate = await refBahrComponentsPage.countDeleteButtons();

    await refBahrComponentsPage.clickOnCreateButton();
    await promise.all([
      refBahrUpdatePage.setCodeInput('code'),
      refBahrUpdatePage.setNameInput('name'),
      refBahrUpdatePage.setSignatureInput('signature'),
      refBahrUpdatePage.styleSelectLastOption(),
      refBahrUpdatePage.parentSelectLastOption()
    ]);
    expect(await refBahrUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await refBahrUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await refBahrUpdatePage.getSignatureInput()).to.eq('signature', 'Expected Signature value to be equals to signature');
    await refBahrUpdatePage.save();
    expect(await refBahrUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await refBahrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RefBahr', async () => {
    const nbButtonsBeforeDelete = await refBahrComponentsPage.countDeleteButtons();
    await refBahrComponentsPage.clickOnLastDeleteButton();

    refBahrDeleteDialog = new RefBahrDeleteDialog();
    expect(await refBahrDeleteDialog.getDialogTitle()).to.eq('metarabApp.refBahr.delete.question');
    await refBahrDeleteDialog.clickOnConfirmButton();

    expect(await refBahrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
