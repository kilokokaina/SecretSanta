function checkAuth() {
    fetch('check_auth').then(async response => {
        let isAuthValid = await response.text();
        switch (isAuthValid) {
            case 'true':
                getUser();
                break;
            case 'false':
                sendAuth();
                break;
        }
    });
}

function getUser() {
    fetch('get_user').then(async response => {
        let telegramUserData = await response.json();
        console.log('User: ' + telegramUserData);
        document.querySelector('#hello-label').innerHTML = `Привет, ${telegramUserData.firstName}👋!`
        getSessions();
    });
}

function sendAuth() {
    fetch('auth', {
        method: 'POST',
        body: window.Telegram.WebApp.initData
    }).then(async response => {
        let telegramUserData = await response.json();
        console.log('Auth success: ' + telegramUserData);
        document.querySelector('#hello-label').innerHTML = `Привет, ${telegramUserData.firstName}👋!`;
        getSessions();
    });
}