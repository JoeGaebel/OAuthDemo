import { Selector } from 'testcafe'

fixture`Google Authentication`
  .page('http://localhost:8081')

const username = ''
const password = ''

test('Google Login Journey', async t => {
  await t.expect(Selector('div').innerText).contains('Sign in with Google')

  await t
    .typeText('#identifierId', username)
    .click('#identifierNext')
    .typeText('[name=password]', password)
    .click('#passwordNext')

  await t
    .expect(Selector('h1').innerText).eql('OAuth Demo')
    .expect(Selector('p').innerText).contains(`${username}@gmail.com`)
})

