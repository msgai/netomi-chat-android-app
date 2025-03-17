package com.netomi.chat.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0019BQ\u0012\u0016\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0014\u0010\f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0010J\u001c\u0010\u0013\u001a\u00020\u000b2\n\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u001c\u0010\u0015\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/netomi/chat/ui/view/NCWFormFilesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/netomi/chat/ui/view/NCWFormFilesAdapter$ViewHolder;", "items", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/FileUploadData;", "Lkotlin/collections/ArrayList;", "isClickable", "", "onDelete", "Lkotlin/Function1;", "", "onRetry", "(Ljava/util/ArrayList;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "()Z", "getItemCount", "", "itemRemoved", "position", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "netomichatsdk_debug"})
public final class NCWFormFilesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.netomi.chat.ui.view.NCWFormFilesAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.netomi.chat.model.messages.FileUploadData> items = null;
    private final boolean isClickable = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.netomi.chat.model.messages.FileUploadData, kotlin.Unit> onDelete = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.netomi.chat.model.messages.FileUploadData, kotlin.Unit> onRetry = null;
    
    public NCWFormFilesAdapter(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.FileUploadData> items, boolean isClickable, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.netomi.chat.model.messages.FileUploadData, kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.netomi.chat.model.messages.FileUploadData, kotlin.Unit> onRetry) {
        super();
    }
    
    public final boolean isClickable() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.netomi.chat.ui.view.NCWFormFilesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.view.NCWFormFilesAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void itemRemoved(int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0019\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u001b\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\u001d\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016\u00a8\u0006\u001f"}, d2 = {"Lcom/netomi/chat/ui/view/NCWFormFilesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/netomi/chat/ui/view/NCWFormFilesAdapter;Landroid/view/View;)V", "constRetry", "Landroidx/constraintlayout/widget/ConstraintLayout;", "getConstRetry", "()Landroidx/constraintlayout/widget/ConstraintLayout;", "icDelete", "Landroid/widget/ImageView;", "getIcDelete", "()Landroid/widget/ImageView;", "progressBar", "Landroid/widget/ProgressBar;", "getProgressBar", "()Landroid/widget/ProgressBar;", "requestDocCard", "getRequestDocCard", "tvConnectionError", "Landroid/widget/TextView;", "getTvConnectionError", "()Landroid/widget/TextView;", "tvDocName", "getTvDocName", "tvDocType", "getTvDocType", "tvRetry", "getTvRetry", "txtCancel", "getTxtCancel", "netomichatsdk_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.constraintlayout.widget.ConstraintLayout requestDocCard = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvDocName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvDocType = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView icDelete = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ProgressBar progressBar = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.constraintlayout.widget.ConstraintLayout constRetry = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView txtCancel = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvConnectionError = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvRetry = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.constraintlayout.widget.ConstraintLayout getRequestDocCard() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvDocName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvDocType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getIcDelete() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ProgressBar getProgressBar() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.constraintlayout.widget.ConstraintLayout getConstRetry() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTxtCancel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvConnectionError() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvRetry() {
            return null;
        }
    }
}