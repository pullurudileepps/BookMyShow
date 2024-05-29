        /*
        1. Check if the user is valid
        2. Showid in showSeatIds and given showId should match
        3. Start transaction (SERIALIZABLE)
        4. select * from show_seats where id in (showSeatIds) and seat_status = 'Available' and show_id = {{showId}} for update
        5. if all seats are not available
        6. throw error and rollback the transaction
        7. Update show_seats set seat_status = 'BLOCKED' where ids in (showSeatIds)
        8. Generate ticket object
        9. Store ticket object in DB and return
         */
