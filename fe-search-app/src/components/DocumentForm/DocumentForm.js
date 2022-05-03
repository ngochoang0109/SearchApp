const DocumentForm = () => {
    return (
        <div className="card-body">
            <form method="post" className="form">
                <div className="form-group">
                    <label>ID</label>
                    <input name="id" className="form-control"></input>
                </div>
                <div className="form-group">
                    <label>Title</label>
                    <input name="title" className="form-control"></input>
                </div>
                <div className="form-group">
                    <label>Content</label> <br></br>
                    <textarea name="content" className="form-control"></textarea>
                </div>
                <div className="form-group">
                    <label>Keywords</label>
                    <input name="keywords" className="form-control"></input>
                </div>
                <input type="submit" value="Update" className="btn btn-danger"></input>
            </form>
        </div>
    )
}

export default DocumentForm;