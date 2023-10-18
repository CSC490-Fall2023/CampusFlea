document.addEventListener("DOMContentLoaded", function () {
    const navItems = document.querySelectorAll(".nav-item");

    navItems.forEach((item) => {
        item.addEventListener("click", () => {
            // Remove the 'active' class from all items
            navItems.forEach((navItem) => {
                navItem.classList.remove("active");
            });

            // Add the 'active' class to the clicked item
            item.classList.add("active");
        });
    });
});