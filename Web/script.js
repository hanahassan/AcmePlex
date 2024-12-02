// Account Dropdown Functionality
const accountIcon = document.getElementById("account-icon");
const accountSection = document.querySelector(".account-section");
const closeAccountButton = document.querySelector(".close-button");
const header = document.querySelector(".header");

// Function to adjust dropdown position dynamically
function adjustDropdownPosition() {
    const headerHeight = header.offsetHeight; // Get the dynamic height of the header
    accountSection.style.top = `${headerHeight}px`; // Set dropdown position based on header height
    document.body.style.paddingTop = `${headerHeight}px`; // Adjust body padding to account for header
}

// Function to reset the dropdown to its initial state
function resetAccountDropdown() {
    loginForm.classList.add("hidden"); // Hide the login form
    signupForm.classList.add("hidden"); // Hide the signup form
    loginPrompt.classList.remove("hidden"); // Show the initial login prompt
}

// Show account dropdown
accountIcon.addEventListener("click", () => {
    if (isLoggedIn()) {
        showProfile();
    } else {
        resetAccountDropdown();
    }
    accountSection.classList.add("active"); // Show dropdown
});

// Hide account dropdown
closeAccountButton.addEventListener("click", () => {
    accountSection.classList.remove("active"); // Hide dropdown
});

// Optional: Close dropdown if clicking outside of it
document.addEventListener("click", (event) => {
    if (
        !accountSection.contains(event.target) &&
        !accountIcon.contains(event.target)
    ) {
        accountSection.classList.remove("active"); // Hide dropdown
    }
});

// Adjust dropdown position on load and resize
window.addEventListener("load", adjustDropdownPosition);
window.addEventListener("resize", adjustDropdownPosition);

// Get elements
const showLoginFormButton = document.getElementById("showLoginForm");
const showSignupFormLink = document.getElementById("showSignupForm");
const loginForm = document.getElementById("loginForm");
const signupForm = document.getElementById("signupForm");
const loginPrompt = document.getElementById("loginPrompt");
const profileSection = document.createElement("div");
profileSection.innerHTML = `
    <p class="profile-name">John Doe</p>
    <p>johndoe@gmail.com</p>
    <p>Address: 123 Main St, Anytown, USA</p>
    <p>Payment Info: **** **** **** 1234</p>
    <button id='logoutButton' class='login-button'>Log Out</button>
`;
profileSection.classList.add("hidden");
profileSection.classList.add("profile-section");
accountSection.appendChild(profileSection);

// Check login state from localStorage
function isLoggedIn() {
    return localStorage.getItem("isLoggedIn") === "true";
}

// Show login form
showLoginFormButton.addEventListener("click", () => {
    loginPrompt.classList.add("hidden"); // Hide the initial prompt
    signupForm.classList.add("hidden"); // Hide the signup form
    loginForm.classList.remove("hidden"); // Show the login form
});

// Show signup form
showSignupFormLink.addEventListener("click", (event) => {
    event.preventDefault(); // Prevent default link behavior
    loginPrompt.classList.add("hidden"); // Hide the initial prompt
    loginForm.classList.add("hidden"); // Hide the login form
    signupForm.classList.remove("hidden"); // Show the signup form
});

// Handle login form submission
loginForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Mock login validation
    if (username === "test@example.com" && password === "password") {
        localStorage.setItem("isLoggedIn", "true");
        showProfile();
        accountSection.classList.remove("active"); // Hide dropdown
        window.location.href = "index.html"; // Redirect to homepage
    } else {
        alert("Invalid login credentials.");
    }
});

// Handle signup form submission
signupForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const signupEmail = document.getElementById("signupEmail").value;
    const signupPassword = document.getElementById("signupPassword").value;
    const confirmSignupPassword = document.getElementById("confirmSignupPassword").value;

    if (signupPassword !== confirmSignupPassword) {
        alert("Passwords do not match.");
        return;
    }

    // Mock signup success
    alert(`Signup successful! Welcome, ${signupEmail}`);
    localStorage.setItem("isLoggedIn", "true");
    accountSection.classList.remove("active"); // Hide dropdown
    window.location.href = "index.html"; // Redirect to homepage
});

// Show profile section
// Show profile section
function showProfile() {
    loginForm.classList.add("hidden");
    signupForm.classList.add("hidden");
    loginPrompt.classList.add("hidden");
    accountSection.classList.add("active");

    // Retrieve user data from localStorage
    const storedEmail = localStorage.getItem("userEmail");
    const storedName = localStorage.getItem("userName") || "John Doe"; // Default to "John Doe" if name is not set

    profileSection.innerHTML = `
        <div class="profile-header">
            <h2>${storedName}</h2>
            <p>${storedEmail}</p>
        </div>
        <div class="profile-options">
            <a href="account-info.html" class="profile-link">
                <div class="profile-item">
                    <h3>Account Information</h3>
                    <p>Profile and payment settings</p>
                </div>
            </a>
            <a href="orders.html" class="profile-link">
                <div class="profile-item">
                    <h3>Orders</h3>
                    <p>View your order history</p>
                </div>
            </a>
        </div>
        <button id="logoutButton" class="login-button">LOG OUT</button>
    `;

    profileSection.classList.remove("hidden");

    // Add the event listener for the logout button here
    document.getElementById("logoutButton").addEventListener("click", handleLogout);
}

// Handle logout
function handleLogout() {
    localStorage.setItem("isLoggedIn", "false");
    profileSection.classList.add("hidden");
    resetAccountDropdown();
    alert("Logged out successfully!");
}
// Search Functionality
const movies = [
    { title: "The Avengers", genre: "Action", image: 'images/TheAvengers.jpg'},
    { title: "Titanic", genre: "Romance", image: 'images/Titanic.jpg' },
    { title: "Inception", genre: "Sci-Fi", image: 'images/Inception.jpg' },
    { title: "The Lion King", genre: "Animation", image: 'images/LionKing.jpg' },
    { title: "Frozen", genre: "Animation", image: 'images/Frozen.jpg' },
];


document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("searchForm");
    const searchInput = document.getElementById("searchInput");
    const searchResults = document.getElementById("searchResults");

    if (searchForm) {
        searchForm.addEventListener("submit", (event) => {
            event.preventDefault(); // Prevent form submission

            const query = searchInput.value.trim().toLowerCase(); // Get search query
            searchResults.innerHTML = ""; // Clear previous results

            if (query) {
                // Filter movies based on the search query
                const filteredMovies = movies.filter((movie) =>
                    movie.title.toLowerCase().includes(query)
                );

                // Display results or "No results found" message
                if (filteredMovies.length > 0) {
                    filteredMovies.forEach((movie) => {
                        const movieItem = document.createElement("div");
                        movieItem.classList.add("movie-item");
                        movieItem.innerHTML = `
                            <h3>${movie.title}</h3>
                            <img src="${movie.image}" alt="${movie.title} Image" class="movie-image">
                            <p>Genre: ${movie.genre}</p>
                            <button class="select-movie-button" onclick="selectMovie(${movie.id})">Select Movie</button>
                        `;
                        searchResults.appendChild(movieItem);
                    });
                } else {
                    searchResults.innerHTML = `<p>No results found for "${query}".</p>`;
                }
            } else {
                searchResults.innerHTML = `<p>Please enter a search term.</p>`;
            }
        });
    }
});

// Function to handle "Select Movie" action
function selectMovie(movieId) {
    window.location.href = `showtimes.html?movie_id=${movieId}`;
}

// Populate Personal Information
function populatePersonalInfo() {
    const mockPersonalInfo = {
        name: "John Doe",
        email: "johndoe@gmail.com",
        address: "123 Main St, Anytown, USA",
    };
    const nameInput = document.getElementById("name");
    const emailInput = document.getElementById("email");
    const addressInput = document.getElementById("address");

    if (nameInput) {
        nameInput.placeholder = mockPersonalInfo.name;
    }
    if (emailInput) {
        emailInput.placeholder = mockPersonalInfo.email;
    }
    if (addressInput) {
        addressInput.placeholder = mockPersonalInfo.address;
    }
}

// Tab Switching Logic
function switchTab(tabId) {
    document.querySelectorAll(".tab-content").forEach((content) => {
        content.classList.add("hidden");
    });
    document.getElementById(tabId).classList.remove("hidden");

    document.querySelectorAll(".tab").forEach((tab) => {
        tab.classList.remove("active");
    });
    if (tabId === "personalInfoSection") {
        document.getElementById("personalInfoTab").classList.add("active");
    } else {
        document.getElementById("paymentInfoTab").classList.add("active");
    }
}

// Tab Event Listeners
const personalInfoTab = document.getElementById("personalInfoTab");
const paymentInfoTab = document.getElementById("paymentInfoTab");

if (personalInfoTab) {
    personalInfoTab.addEventListener("click", () => {
        switchTab("personalInfoSection");
    });
}

if (paymentInfoTab) {
    paymentInfoTab.addEventListener("click", () => {
        switchTab("paymentInfoSection");
    });
}

// Mock Data
const mockCredits = 45.50; // Example user credits
const mockOrders = [
    {
        orderId: 101,
        showtime: "Avengers - 01/12/2024 at 5:00 PM",
        seat: "A10",
        price: "$12.50",
        purchaseDate: "2024-11-28 14:30:00",
        status: "Confirmed"
    },
    {
        orderId: 102,
        showtime: "Inception - 03/12/2024 at 7:00 PM",
        seat: "B12",
        price: "$15.00",
        purchaseDate: "2024-11-29 16:00:00",
        status: "Cancelled"
    }
];

// Populate Credits
function displayCredits() {
    const creditsDiv = document.getElementById("credits");
    if (creditsDiv) {
        creditsDiv.innerHTML = `Available Credits: <strong>$${mockCredits.toFixed(2)}</strong>`;
        creditsDiv.classList.add("credits");
    }
}


// Populate Orders Table
// Populate Orders Table with Separate Boxes
function displayOrders() {
    const ordersList = document.getElementById("orders-list");
    if (ordersList) {
        ordersList.innerHTML = ""; // Clear previous orders
        mockOrders.forEach(order => {
            // Create a container for each order
            const orderCard = document.createElement("div");
            orderCard.classList.add("order-card");

            // Add order details to the card
            orderCard.innerHTML = `
                <p><strong>Order ID:</strong> ${order.orderId}</p>
                <p><strong>Showtime:</strong> ${order.showtime}</p>
                <p><strong>Seat:</strong> ${order.seat}</p>
                <p><strong>Price:</strong> ${order.price}</p>
                <p><strong>Purchase Date:</strong> ${order.purchaseDate}</p>
                <p><strong>Status:</strong> <span class="${order.status.toLowerCase()}">${order.status}</span></p>
            `;

            // Append the order card to the orders list
            ordersList.appendChild(orderCard);
        });
    }
}


// Initialize Page
document.addEventListener("DOMContentLoaded", () => {
    populatePersonalInfo();
    displayCredits();
    displayOrders();
    if (isLoggedIn()) {
        showProfile();
        accountSection.classList.remove("active");
    }
    const movieGrid = document.querySelector('.movie-grid');
    
    if (movieGrid) {
        fetchMovies();
    }

    const theatresList = document.querySelector('.theatres-list');
    
    if (theatresList) {
        fetchTheatres();
    }
});


function fetchMovies() {
    // Fetch movies from the backend API
    fetch('http://localhost:8080/movies')
        .then(response => response.json())
        .then(movies => {
            // Populate Movie Grid
            const movieGrid = document.querySelector('.movie-grid');
            movieGrid.innerHTML = ''; // Clear existing content

            movies.forEach((movie, index) => {
                // Determine which image to use
                const posterUrl = movie.posterUrl || `images/filler${index + 1}.png`; // Unique fallback for each movie

                const movieCard = document.createElement('div');
                movieCard.classList.add('movie-card');
                movieCard.innerHTML = `
                    <img src="${posterUrl}" alt="${movie.title}" />
                    <h3>${movie.title}</h3>
                `;
                movieGrid.appendChild(movieCard);
            });
        })
        .catch(error => console.error('Error fetching movies:', error));

}

function fetchTheatres() {
    // Fetch theatres from the backend API
    fetch('http://localhost:8080/theatres')
        .then(response => response.json())
        .then(theatres => {
            // Populate Theatre List
            const theatresList = document.querySelector('.theatres-list');
            theatresList.innerHTML = ''; // Clear existing content

            theatres.forEach(theatre => {
                const theatreButton = document.createElement('button');
                theatreButton.classList.add('theatre-button');
                theatreButton.setAttribute('aria-label', theatre.name);
                theatreButton.setAttribute('data-testid', `theatre-id-${theatre.theatre_id}`);
                theatreButton.onclick = () => {
                    // Redirect to tickets page with the theatre_id as a query parameter
                    window.location.href = `tickets.html?theatre_id=${theatre.theatre_id}`;
                };
                
                // Adding theatre name and location to the button
                theatreButton.innerHTML = `
                    <div class="theatre-name">${theatre.name}</div>
                    <div class="location">${theatre.location}</div>
                `;
                
                // Append the new theatre button to the list
                theatresList.appendChild(theatreButton);
            });
        })
        .catch(error => console.error('Error fetching theatres:', error));
}

// Handle signup form submission
// Handle signup form submission
signupForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const signupEmail = document.getElementById("signupEmail").value;
    const signupPassword = document.getElementById("signupPassword").value;
    const confirmSignupPassword = document.getElementById("confirmSignupPassword").value;
    const signupName = document.getElementById("signupName").value; // Name input

    if (signupPassword !== confirmSignupPassword) {
        alert("Passwords do not match.");
        return;
    }

    // Save to localStorage
    localStorage.setItem("userEmail", signupEmail);
    localStorage.setItem("userPassword", signupPassword);
    localStorage.setItem("userName", signupName); // Save user's name

    alert(`Signup successful! Welcome, ${signupName}`);
    localStorage.setItem("isLoggedIn", "true");

    window.location.href = "index.html"; // Redirect or update page
});

// Handle login form submission
loginForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Check credentials against stored data
    const storedEmail = localStorage.getItem("userEmail");
    const storedPassword = localStorage.getItem("userPassword");

    if (username === storedEmail && password === storedPassword) {
        localStorage.setItem("isLoggedIn", "true");
        showProfile();
        accountSection.classList.remove("active"); // Hide dropdown
        window.location.href = "index.html"; // Redirect to homepage
    } else {
        alert("Invalid login credentials.");
    }
});
