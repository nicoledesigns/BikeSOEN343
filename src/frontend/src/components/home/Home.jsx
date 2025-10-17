import React from "react";

const Home = () => {
    // Retrieve full name from localStorage
    const fullName = localStorage.getItem('user_full_name');
    const role = localStorage.getItem('user_role');

    return (
        <div style={{ padding: '16px' }}>
            <header style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <h1>Hello{fullName ? `, ${fullName}` : ''}!</h1>
                <h1>Hello{role ? `, ${role}` : ''}!</h1>
            </header>

            <main>
                {/* existing home content here */}
                <p>Welcome to the app.</p>
            </main>
        </div>
    );
};

export default Home;
