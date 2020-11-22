import React from 'react'
import Cookies from 'js-cookie'
import { api, googleId } from './Api'
import { Redirect } from 'react-router-dom'
import GoogleLogin from 'react-google-login';
function Home() {
    const [userstate, setuserstate] = React.useState('index');
    const onGoogleSignIn = (googleUser) => {
        console.log("inside")
        console.log(googleUser)
        console.log(googleUser.profileObj)
        var profile = googleUser.profileObj;
        localStorage.setItem('username', profile.name);
        localStorage.setItem('userImage', profile.imageUrl);
        Cookies.set('username', profile.name);
        Cookies.set('userImage', profile.imageUrl);
        var email = profile.email
        api.post('?oper-type=uservisit&email=' + email)
            .then((response) => {
                console.log(response.data)
                return response.data.data;
            })
            .then(dta => {
                Cookies.set('user-id', dta.uesrId);
                // note here we can use json web token
                if (dta.newUser === true) {
                    setuserstate("newuser")
                } else {
                    setuserstate("user")
                }
            })
            .catch(err => {
                console.log(err)
            })

    }
    const test = () => {
        var email = `test1998@gmail.com`
        api.post('?oper-type=uservisit&email=' + email)
            .then((response) => {
                console.log(response.data)
                return response.data.data;
            })
            .then(dta => {
                Cookies.set('user-id', dta.uesrId);
                // note here we can use json web token
                if (dta.newUser === true) {
                    setuserstate("newuser")
                } else {
                    setuserstate("user")
                }
            })
            .catch(err => {
                console.log(err)
            })

    }
    const loadView = () => {
        if (userstate === "newuser") {
            /* <div class="g-signin2" data-onsuccess={onSignIn}></div> */
            return <Redirect to="/SimpleLogin/changerole" />
        }
        else if (userstate === "user") {
            return <Redirect to="/SimpleLogin/profile" />
        }
        else {
            /* <div class="g-signin2" data-onsuccess={onGoogleSignIn}></div> */
            //return <button onClick={test}>HHH</button>
            return <><div><GoogleLogin
                clientId={googleId}
                onSuccess={res => {
                    onGoogleSignIn(res)
                }}
            //onFailure={responseGoogle}
            /></div><br /><button onClick={test}>HHH</button></>
        }
    }
    return (
        loadView()
    )
}

export default Home
